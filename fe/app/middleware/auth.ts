export default defineNuxtRouteMiddleware(async () => {
  const { token, user, fetchCurrentUser } = useAuth();

  // SSR: lấy token từ cookie
  if (import.meta.server) {
    const event = useRequestEvent();
    const cookieToken = getCookie(event, 'auth_token');

    if (cookieToken) {
      token.value = cookieToken;
    }
  }

  // Client: F5 hoặc chuyển trang -> lấy lại token từ localStorage
  if (import.meta.client && !token.value) {
    const storedToken = localStorage.getItem('auth_token');

    if (storedToken) {
      token.value = storedToken;
    }
  }

  // Có token nhưng chưa có user -> lấy user từ backend
  if (token.value && !user.value) {
    try {
      await fetchCurrentUser();
    } catch {
      return navigateTo('/login');
    }
  }

  // Không authenticated
  if (!token.value || !user.value) {
    return navigateTo('/login');
  }
});