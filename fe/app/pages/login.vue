<template>
  <div class="min-h-screen bg-[#f5f5f5] flex items-center justify-center px-4">
    <div class="bg-white rounded-3xl shadow-[0_20px_60px_rgba(0,0,0,0.08)] border border-black/5 p-8 w-full max-w-md overflow-hidden">
      <div class="h-1.5 -mx-8 -mt-8 mb-8 bg-gradient-to-r from-[#7040ff] via-[#863cff] to-[#a33cff]"></div>

      <div class="text-center mb-8">
        <div class="w-11 h-11 mx-auto mb-5 rounded-2xl bg-black flex items-center justify-center">
          <span class="text-white text-lg font-bold px-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="100%" height="100%">
              <!-- Thân tay cầm dạng nét viền (Outline) màu trắng -->
              <path 
                d="M6 18h12c2.2 0 4-1.8 4-4V9c0-2.2-1.8-4-4-4H6C3.8 5 2 6.8 2 9v5c0 2.2 1.8 4 4 4z" 
                fill="none" 
                stroke="#FFFFFF" 
                stroke-width="2" 
                stroke-linecap="round" 
                stroke-linejoin="round"
              />

              <!-- D-Pad (Phím điều hướng bên trái) -->
              <path 
                d="M6 9v4M4 11h4" 
                stroke="#FFFFFF" 
                stroke-width="2" 
                stroke-linecap="round"
              />

              <!-- Các nút bấm bên phải -->
              <circle cx="15.5" cy="12.5" r="1" fill="#FFFFFF" />
              <circle cx="18" cy="10" r="1" fill="#FFFFFF" />
            </svg>
          </span>
        </div>

        <h1 class="text-3xl font-bold tracking-tight text-black mb-2">Welcome Back</h1>
        <p class="text-sm text-black/45">Sign in to your account</p>
      </div>

      <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-xl mb-5 text-sm">
        {{ error }}
      </div>

      <form @submit.prevent="handleLogin" class="space-y-5">
        <div>
          <label for="username" class="block text-[13px] font-semibold text-black mb-2">Username</label>
          <input
            id="username"
            v-model="form.username"
            type="text"
            required
            class="w-full px-4 py-3 border border-black/10 rounded-xl bg-[#fafafa] text-sm text-black placeholder:text-black/30 outline-none focus:bg-white focus:border-black focus:ring-4 focus:ring-black/[0.04] transition-all"
            placeholder="Enter your username"
          />
        </div>

        <div>
          <label for="password" class="block text-[13px] font-semibold text-black mb-2">Password</label>
          <input
            id="password"
            v-model="form.password"
            type="password"
            required
            class="w-full px-4 py-3 border border-black/10 rounded-xl bg-[#fafafa] text-sm text-black placeholder:text-black/30 outline-none focus:bg-white focus:border-black focus:ring-4 focus:ring-black/[0.04] transition-all"
            placeholder="Enter your password"
          />
        </div>

        <button
          type="submit"
          :disabled="loading"
          class="w-full bg-black text-white py-3 px-4 rounded-xl font-semibold text-sm hover:bg-gradient-to-r hover:from-[#7040ff] hover:via-[#863cff] hover:to-[#a33cff] focus:ring-4 focus:ring-[#863cff]/20 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ loading ? 'Signing in...' : 'Sign In' }}
        </button>
      </form>

      <div class="mt-6 pt-6 border-t border-black/5 text-center">
        <p class="text-sm text-black/40">
          Don't have an account?
          <NuxtLink to="/register" class="text-black hover:text-[#863cff] font-semibold transition-colors">Sign up</NuxtLink>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useAuth } from '../composables/useAuth';
import { useRouter } from 'vue-router';

const auth = useAuth();
const router = useRouter();

const form = ref({
  username: '',
  password: ''
});

const loading = ref(false);
const error = ref('');

const handleLogin = async () => {
  if (!form.value.username || !form.value.password) {
    error.value = 'Please fill in all fields';
    return;
  }

  try {
    loading.value = true;
    error.value = '';
    
    await auth.login(form.value);
    
    // Redirect to game page
    router.push('/game');
  } catch (err: any) {
    error.value = err.message || 'Login failed';
  } finally {
    loading.value = false;
  }
};
</script>