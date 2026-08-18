<template>
  <ClientOnly>
    <div class="min-h-screen bg-[#f5f5f5] px-4 py-6 sm:px-6 lg:px-8">
      <div class="container mx-auto max-w-5xl">

        <!-- Header -->
        <div class="relative overflow-hidden bg-gradient-to-br from-[#17153f] via-[#30206b] to-[#5b21b6] rounded-[28px] shadow-[0_20px_60px_rgba(79,45,180,0.18)] p-6 sm:p-8 mb-6">

          <!-- Glow -->
          <div class="absolute -top-32 -right-32 w-72 h-72 bg-[#863cff]/20 rounded-full blur-[90px]"></div>
          <div class="absolute -bottom-32 -left-32 w-72 h-72 bg-[#7040ff]/15 rounded-full blur-[90px]"></div>

          <!-- Gradient top -->
          <div class="absolute top-0 left-0 right-0 h-1.5 bg-gradient-to-r from-[#7040ff] via-[#863cff] to-[#a33cff]"></div>

          <div class="relative z-10">
            <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-5 mb-7">

              <div class="flex items-center gap-4">
                <!-- Logo -->
                <div class="w-12 h-12 shrink-0 rounded-2xl bg-white flex items-center justify-center shadow-[0_8px_30px_rgba(134,60,255,0.2)]">
                  <span class="w-7 h-7">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="100%" height="100%">
                      <path
                        d="M6 18h12c2.2 0 4-1.8 4-4V9c0-2.2-1.8-4-4-4H6C3.8 5 2 6.8 2 9v5c0 2.2 1.8 4 4 4z"
                        fill="none"
                        stroke="#000000"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M6 9v4M4 11h4"
                        stroke="#000000"
                        stroke-width="2"
                        stroke-linecap="round"
                      />
                      <circle cx="15.5" cy="12.5" r="1" fill="#000000" />
                      <circle cx="18" cy="10" r="1" fill="#000000" />
                    </svg>
                  </span>
                </div>

                <div>
                  <h1 class="text-2xl sm:text-3xl font-bold tracking-tight text-white">
                    Number Guessing
                  </h1>
                  <p class="text-sm text-white/40 mt-1">
                    Guess a number between 1 and 5
                  </p>
                </div>
              </div>

              <button
                @click="handleLogout"
                class="group inline-flex items-center gap-2 bg-white/10 border border-white/10 text-white/70 px-4 py-2.5 rounded-xl text-sm font-semibold hover:bg-white hover:text-black transition-all"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  class="w-4 h-4"
                >
                  <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                  <path d="m16 17 5-5-5-5" />
                  <path d="M21 12H9" />
                </svg>
                Logout
              </button>
            </div>

            <!-- Stats -->
            <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">

              <div class="bg-white/[0.06] border border-white/[0.08] rounded-2xl p-4">
                <div class="flex items-center justify-between mb-2">
                  <div class="text-xs text-white/40 font-medium uppercase tracking-wider">
                    Score
                  </div>

                  <div class="w-8 h-8 rounded-lg bg-[#7040ff]/20 flex items-center justify-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      stroke-width="2"
                      class="w-4 h-4 text-[#a33cff]"
                    >
                      <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" />
                    </svg>
                  </div>
                </div>

                <div class="text-3xl font-bold text-white">
                  {{ user?.score || 0 }}
                </div>
              </div>

              <div class="bg-white/[0.06] border border-white/[0.08] rounded-2xl p-4">
                <div class="flex items-center justify-between mb-2">
                  <div class="text-xs text-white/40 font-medium uppercase tracking-wider">
                    Turns Remaining
                  </div>

                  <div class="w-8 h-8 rounded-lg bg-[#863cff]/20 flex items-center justify-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      stroke-width="2"
                      class="w-4 h-4 text-[#863cff]"
                    >
                      <circle cx="12" cy="12" r="9" />
                      <path d="M12 7v5l3 2" />
                    </svg>
                  </div>
                </div>

                <div class="text-3xl font-bold text-white">
                  {{ user?.turns || 0 }}
                </div>
              </div>

              <div class="bg-white/[0.06] border border-white/[0.08] rounded-2xl p-4">
                <div class="flex items-center justify-between mb-2">
                  <div class="text-xs text-white/40 font-medium uppercase tracking-wider">
                    Total Guesses
                  </div>

                  <div class="w-8 h-8 rounded-lg bg-[#a33cff]/20 flex items-center justify-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      stroke-width="2"
                      class="w-4 h-4 text-[#a33cff]"
                    >
                      <path d="M4 4h16v16H4z" />
                      <path d="M8 8h8M8 12h5M8 16h8" />
                    </svg>
                  </div>
                </div>

                <div class="text-3xl font-bold text-white">
                  {{ totalGuesses }}
                </div>
              </div>

            </div>
          </div>
        </div>

        <!-- Game Area -->
        <div class="bg-white rounded-[28px] shadow-[0_20px_60px_rgba(0,0,0,0.07)] border border-black/5 p-6 sm:p-8 mb-6">

          <div v-if="loading" class="text-center py-12">
            <div class="inline-block animate-spin rounded-full h-12 w-12 border-2 border-black/10 border-t-[#863cff]"></div>
            <p class="mt-5 text-sm text-black/40">
              Processing your guess...
            </p>
          </div>

          <div v-else>

            <!-- Guess Form -->
            <div class="mb-8">
              <div class="flex items-center justify-between mb-5">
                <div>
                  <h2 class="text-xl font-bold tracking-tight text-black">
                    Make Your Guess
                  </h2>
                  <p class="text-sm text-black/35 mt-1">
                    Pick the number you think is hidden
                  </p>
                </div>

                <div class="hidden sm:flex items-center gap-2 text-xs text-black/30">
                  <span class="w-2 h-2 rounded-full bg-[#863cff]"></span>
                  Your choice
                </div>
              </div>

              <div class="grid grid-cols-5 gap-2 sm:gap-3 mb-5 ">
                <button
                  v-for="num in numbers"
                  :key="num"
                  @click="selectNumber(num)"
                  :class="buttonClass(num)"
                  :disabled="!canGuess || loading"
                  class="!rounded-2xl !py-5 !text-xl !font-bold transition-all"
                >
                  {{ num }}
                </button>
              </div>

              <button
                @click="makeGuess"
                :disabled="!canGuess || loading || selectedNumber === null"
                class="group w-full bg-black text-white py-3.5 px-6 rounded-xl font-semibold text-sm hover:bg-gradient-to-r hover:from-[#7040ff] hover:via-[#863cff] hover:to-[#a33cff] focus:ring-4 focus:ring-[#863cff]/20 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span class="inline-flex items-center justify-center gap-2">
                  {{ loading ? 'Guessing...' : 'Submit Guess' }}

                  <svg
                    v-if="!loading"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    class="w-4 h-4 group-hover:translate-x-1 transition-transform"
                  >
                    <path d="M5 12h14" />
                    <path d="m13 6 6 6-6 6" />
                  </svg>
                </span>
              </button>
            </div>

            <!-- Result Display -->
            <div
              v-if="lastResult"
              class="rounded-2xl bg-[#fafafa] border border-black/5 p-5 sm:p-6"
            >
              <div class="flex items-center justify-between mb-5">
                <div>
                  <h3 class="text-base font-bold text-black">
                    Last Result
                  </h3>
                  <p class="text-xs text-black/35 mt-1">
                    Here's how your latest guess went
                  </p>
                </div>

                <div
                  class="px-3 py-1.5 rounded-full text-[11px] font-bold"
                  :class="lastResult.correct
                    ? 'bg-green-50 text-green-600'
                    : 'bg-red-50 text-red-600'"
                >
                  {{ lastResult.correct ? 'SUCCESS' : 'FAILED' }}
                </div>
              </div>

              <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">

                <div class="bg-white border border-black/5 rounded-xl p-4 text-center">
                  <div class="text-xs text-black/35 mb-2">Your Guess</div>
                  <div class="text-2xl font-bold text-black">
                    {{ lastResult.guess }}
                  </div>
                </div>

                <div class="bg-white border border-black/5 rounded-xl p-4 text-center">
                  <div class="text-xs text-black/35 mb-2">Server Number</div>
                  <div
                    class="text-2xl font-bold"
                    :class="lastResult.correct ? 'text-green-600' : 'text-red-600'"
                  >
                    {{ lastResult.serverNumber }}
                  </div>
                </div>

                <div class="bg-white border border-black/5 rounded-xl p-4 text-center">
                  <div class="text-xs text-black/35 mb-2">Result</div>
                  <div
                    class="text-lg font-bold"
                    :class="lastResult.correct ? 'text-green-600' : 'text-red-600'"
                  >
                    {{ lastResult.correct ? 'CORRECT!' : 'WRONG' }}
                  </div>
                </div>

              </div>
            </div>

          </div>
        </div>

        <!-- Buy Turns Section -->
        <div class="relative overflow-hidden bg-gradient-to-br from-[#17153f] via-[#30206b] to-[#5b21b6] rounded-[28px] shadow-[0_20px_60px_rgba(0,0,0,0.12)] p-6 sm:p-7">

          <div class="absolute -right-20 -bottom-20 w-56 h-56 bg-[#863cff]/20 rounded-full blur-[70px]"></div>

          <div class="relative z-10 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-5">

            <div>
              <div class="flex items-center gap-2 mb-2">
                <span class="w-2 h-2 rounded-full bg-[#a33cff] shadow-[0_0_10px_#a33cff]"></span>
                <span class="text-xs font-semibold uppercase tracking-wider text-white/40">
                  Extra Attempts
                </span>
              </div>

              <h2 class="text-xl font-bold text-white mb-2">
                Need More Turns?
              </h2>

              <p class="text-sm text-white/40">
                Buy 5 additional turns to continue playing
              </p>

              <p class="text-xs text-white/25 mt-1">
                Cost: Free (for testing)
              </p>
            </div>

            <button
              @click="buyTurns"
              :disabled="loading || user?.turns >= 20"
              class="w-full sm:w-auto bg-white text-black px-6 py-3 rounded-xl font-semibold text-sm hover:bg-gradient-to-r hover:from-[#7040ff] hover:via-[#863cff] hover:to-[#a33cff] hover:text-white transition-all disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {{ loading ? 'Buying...' : 'Buy 5 Turns' }}
            </button>

          </div>
        </div>

        <!-- Error Display -->
        <div
          v-if="error"
          class="mt-6 bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-xl text-sm"
        >
          {{ error }}
        </div>

        <!-- Footer -->
        <div class="text-center py-6">
          <p class="text-xs text-black/25">
            Number Guessing Game
            <span class="mx-1">•</span>
            Think smart. Guess better.
          </p>
        </div>

      </div>
    </div>
  </ClientOnly>
</template>

<script setup lang="ts">
definePageMeta({
  middleware: 'auth'
});
import { ref, computed, onMounted } from 'vue';
import { useAuth } from '../composables/useAuth';
import { useRouter } from 'vue-router';

const auth = useAuth();
const router = useRouter();

const selectedNumber = ref<number | null>(null);
const lastResult = ref<any>(null);
const loading = ref(false);
const error = ref('');

const numbers = [1, 2, 3, 4, 5];

const user = computed(() => auth.user.value);

const canGuess = computed(() => {
  return user.value && user.value.turns > 0 && !loading.value;
});

const totalGuesses = computed(() => {
  // This would need to be tracked from backend or calculated
  return 0;
});

const buttonClass = (num: number) => {
  const base = 'w-16 h-16 rounded-lg font-semibold transition-all transform hover:scale-105 ';
  const selected = selectedNumber.value === num;
  const disabled = !canGuess.value || loading.value;
  
  if (disabled) {
    return base + 'bg-gray-300 text-gray-500 cursor-not-allowed';
  }
  
  if (selected) {
    return base + 'bg-blue-600 text-white border-2 border-blue-800';
  }
  
  return base + 'bg-white text-gray-700 border-2 border-gray-300 hover:border-blue-400';
};

const selectNumber = (num: number) => {
  if (!canGuess.value || loading.value) return;
  selectedNumber.value = num;
};

const makeGuess = async () => {
  if (!selectedNumber.value || !canGuess.value) return;
  
  try {
    loading.value = true;
    error.value = '';
    
    const result = await auth.guess(selectedNumber.value);
    lastResult.value = result;
    selectedNumber.value = null;
    
    // Show success message
    setTimeout(() => {
      lastResult.value = null;
    }, 3000);
    
  } catch (err: any) {
    error.value = err.message || 'Failed to make guess';
  } finally {
    loading.value = false;
  }
};

const buyTurns = async () => {
  console.log('canGuess:', canGuess.value, 'loading:', loading.value, 'user turns:', user.value?.turns);
  console.log('Attempting to buy turns. Current turns:', user.value?.turns);
  if (loading.value) return;
  
  try {
    loading.value = true;
    error.value = '';
    
    await auth.buyTurns();
    
  } catch (err: any) {
    error.value = err.message || 'Failed to buy turns';
  } finally {
    loading.value = false;
  }
};

const handleLogout = () => {
  auth.logout();
  router.push('/login');
};

onMounted(() => {
  if (!auth.isAuthenticated.value) {
    router.push('/login');
  }
});
</script>