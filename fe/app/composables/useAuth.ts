import { ref, computed } from 'vue';
import { useFetch, useRuntimeConfig } from '#imports';

export interface User {
  username: string;
  score: number;
  turns: number;
}

export interface AuthResponse {
  token: string;
  username: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
}

export interface GuessRequest {
  guess: number;
}

export interface GuessResponse {
  correct: boolean;
  guess: number;
  serverNumber: number;
  score: number;
  turns: number;
}

export interface UserSummary {
  username: string;
  score: number;
  turns: number;
}

export interface LeaderboardEntry {
  username: string;
  score: number;
}

const token = ref<string | null>(null);
const user = ref<User | null>(null);
const loading = ref(false);
const error = ref<string | null>(null);

export function useAuth() {
  const config = useRuntimeConfig();
  const apiBase = config.public.apiBase;
  const login = async (credentials: LoginRequest): Promise<AuthResponse> => {
    loading.value = true;
    error.value = null;

    try {
      const response = await $fetch<AuthResponse>(`${apiBase}/auth/login`, {
        method: 'POST',
        body: credentials,
        headers: {
          'Content-Type': 'application/json'
        }
      });

      token.value = response.token;
      localStorage.setItem('auth_token', response.token);
      document.cookie = `auth_token=${response.token}; path=/; max-age=${60 * 60 * 24 * 7}`;

      // Fetch user info
      await fetchCurrentUser();

      return response;
    } catch (err: any) {
      error.value = err.data?.message || 'Login failed';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const register = async (userData: RegisterRequest): Promise<AuthResponse> => {
    loading.value = true;
    error.value = null;

    try {
      const response = await $fetch<AuthResponse>(`${apiBase}/auth/register`, {
        method: 'POST',
        body: userData,
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (!response?.token) {
        throw new Error('Registration succeeded but no authentication token was returned');
      }

      token.value = response.token;
      //Đáng lẽ phải lưu cookie httpOnly nhưng do FE và BE sẽ được deloy trên MT khác nhau nên không thể set cookie httpOnly được. Nên tạm thời lưu token vào localStorage để tiện cho việc deloy free.
      localStorage.setItem('auth_token', response.token);
      document.cookie = `auth_token=${response.token}; path=/; max-age=${60 * 60 * 24 * 7}`;

      await fetchCurrentUser();

      return response;
    } catch (err: any) {
      token.value = null;
      localStorage.removeItem('auth_token');

      error.value = err.data?.message || err.message || 'Registration failed';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const logout = () => {
    token.value = null;
    user.value = null;
    localStorage.removeItem('auth_token');
  };

  const fetchCurrentUser = async () => {
    if (!token.value) return;

    loading.value = true;
    error.value = null;

    try {
      const userData = await $fetch<UserSummary>(`${apiBase}/user/me`, {
        headers: {
          'Authorization': `Bearer ${token.value}`
        }
      });

      user.value = userData;
    } catch (err: any) {
      error.value = err.data?.message || 'Failed to fetch user data';
      if (err.status === 401) {
        logout();
      }
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const guess = async (guess: number): Promise<GuessResponse> => {
    if (!token.value) {
      throw new Error('Not authenticated');
    }

    loading.value = true;
    error.value = null;

    try {
      const response = await $fetch<GuessResponse>(`${apiBase}/game/guess`, {
        method: 'POST',
        body: { guess },
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token.value}`
        }
      });

      // Update local user state
      if (user.value) {
        user.value.score = response.score;
        user.value.turns = response.turns;
      }

      return response;
    } catch (err: any) {
      error.value = err.data?.message || 'Guess failed';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const buyTurns = async (): Promise<UserSummary> => {
    if (!token.value) {
      throw new Error('Not authenticated');
    }

    loading.value = true;
    error.value = null;

    try {
      const response = await $fetch<UserSummary>(`${apiBase}/game/buy-turns`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token.value}`
        }
      });

      if (user.value) {
        user.value.turns = response.turns;
      }

      return response;
    } catch (err: any) {
      error.value = err.data?.message || 'Failed to buy turns';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const fetchLeaderboard = async (): Promise<LeaderboardEntry[]> => {
    loading.value = true;
    error.value = null;

    try {
      return await $fetch<LeaderboardEntry[]>(`${apiBase}/game/leaderboard`);
    } catch (err: any) {
      error.value = err.data?.message || 'Failed to fetch leaderboard';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const isAuthenticated = computed(() => !!token.value && !!user.value);

  return {
    token,
    user,
    loading,
    error,
    login,
    register,
    logout,
    guess,
    buyTurns,
    fetchLeaderboard,
    isAuthenticated,
    fetchCurrentUser
  };
}
