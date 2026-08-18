<template>
  <ClientOnly>
    <div class="min-h-screen bg-[#f5f5f5]">
      <div class="container mx-auto px-4 py-8 max-w-4xl">
        <!-- Header -->
        <div class="relative overflow-hidden bg-black rounded-[28px] shadow-[0_20px_60px_rgba(0,0,0,0.12)] p-6 sm:p-8 mb-6">
          <div class="absolute inset-0 bg-gradient-to-br from-[#7040ff] via-[#863cff] to-[#a33cff] opacity-90"></div>
          <div class="absolute -right-16 -top-16 w-48 h-48 bg-white/10 rounded-full blur-2xl"></div>
          <div class="absolute -left-12 -bottom-20 w-40 h-40 bg-white/10 rounded-full blur-2xl"></div>

          <div class="relative flex items-center gap-4">
            <div class="w-12 h-12 rounded-2xl bg-black/25 border border-white/20 flex items-center justify-center shrink-0">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="28"
                height="28"
              >
                <path
                  d="M6 18h12c2.2 0 4-1.8 4-4V9c0-2.2-1.8-4-4-4H6C3.8 5 2 6.8 2 9v5c0 2.2 1.8 4 4 4z"
                  fill="none"
                  stroke="#FFFFFF"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />

                <path
                  d="M6 9v4M4 11h4"
                  stroke="#FFFFFF"
                  stroke-width="2"
                  stroke-linecap="round"
                />

                <circle cx="15.5" cy="12.5" r="1" fill="#FFFFFF" />
                <circle cx="18" cy="10" r="1" fill="#FFFFFF" />
              </svg>
            </div>

            <div>
              <h1 class="text-3xl font-bold tracking-tight text-white mb-1">
                Leaderboard
              </h1>
              <p class="text-sm text-white/65">
                Top 10 players by score
              </p>
            </div>
          </div>
        </div>

        <!-- Leaderboard Content -->
        <div class="bg-white rounded-[28px] shadow-[0_20px_60px_rgba(0,0,0,0.08)] border border-black/5 overflow-hidden">
          <div v-if="loading" class="text-center py-12">
            <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-black/10 border-t-[#863cff]"></div>
            <p class="mt-4 text-sm text-black/45">
              Loading leaderboard...
            </p>
          </div>

          <div v-else-if="error" class="p-6">
            <div class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-xl text-sm">
              {{ error }}
            </div>
          </div>

          <div v-else-if="leaderboard.length === 0" class="text-center py-12 px-6">
            <div class="bg-[#fafafa] border border-black/5 rounded-2xl p-8">
              <div class="w-12 h-12 mx-auto mb-4 rounded-2xl bg-black flex items-center justify-center">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  width="26"
                  height="26"
                >
                  <path
                    d="M6 18h12c2.2 0 4-1.8 4-4V9c0-2.2-1.8-4-4-4H6C3.8 5 2 6.8 2 9v5c0 2.2 1.8 4 4 4z"
                    fill="none"
                    stroke="#FFFFFF"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M6 9v4M4 11h4"
                    stroke="#FFFFFF"
                    stroke-width="2"
                    stroke-linecap="round"
                  />
                  <circle cx="15.5" cy="12.5" r="1" fill="#FFFFFF" />
                  <circle cx="18" cy="10" r="1" fill="#FFFFFF" />
                </svg>
              </div>

              <h3 class="text-xl font-semibold text-black mb-2">
                No players yet
              </h3>

              <p class="text-sm text-black/40">
                Be the first to join the game!
              </p>
            </div>
          </div>

          <div v-else class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-[#fafafa] border-b border-black/5">
                  <th class="px-6 py-4 text-left text-[12px] uppercase tracking-wider font-semibold text-black/40">
                    Rank
                  </th>

                  <th class="px-6 py-4 text-left text-[12px] uppercase tracking-wider font-semibold text-black/40">
                    Username
                  </th>

                  <th class="px-6 py-4 text-left text-[12px] uppercase tracking-wider font-semibold text-black/40">
                    Score
                  </th>
                </tr>
              </thead>

              <tbody class="divide-y divide-black/5">
                <tr
                  v-for="(entry, index) in leaderboard"
                  :key="entry.username"
                  class="group hover:bg-[#fafafa] transition-colors"
                >
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div
                      class="w-9 h-9 rounded-xl flex items-center justify-center"
                      :class="
                        index === 0
                          ? 'bg-gradient-to-br from-[#7040ff] to-[#a33cff] text-white'
                          : 'bg-black/[0.04] text-black/60'
                      "
                    >
                      <span class="text-sm font-bold">
                        {{ index + 1 }}
                      </span>
                    </div>
                  </td>

                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm font-semibold text-black">
                      {{ entry.username }}
                    </div>
                  </td>

                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="inline-flex items-center px-3 py-1.5 rounded-lg bg-[#863cff]/10 text-[#7040ff] text-sm font-bold">
                      {{ entry.score }}
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Back to Game Button -->
        <div class="mt-6 text-center">
          <NuxtLink
            to="/game"
            class="inline-flex items-center justify-center bg-black text-white px-6 py-3 rounded-xl font-semibold text-sm hover:bg-gradient-to-r hover:from-[#7040ff] hover:via-[#863cff] hover:to-[#a33cff] focus:ring-4 focus:ring-[#863cff]/20 transition-all"
          >
            Back to Game
          </NuxtLink>
        </div>
      </div>
    </div>
  </ClientOnly>
</template>

<script setup lang="ts">
definePageMeta({
  middleware: 'auth'
});
import { ref, onMounted } from 'vue';
import { useAuth } from '../composables/useAuth';
import { useRouter } from 'vue-router';

const auth = useAuth();
const router = useRouter();

const leaderboard = ref<any[]>([]);
const loading = ref(false);
const error = ref('');

const fetchLeaderboard = async () => {
  try {
    loading.value = true;
    error.value = '';
    leaderboard.value = await auth.fetchLeaderboard();
  } catch (err: any) {
    error.value = err.message || 'Failed to fetch leaderboard';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  if (!auth.isAuthenticated.value) {
    router.push('/login');
    return;
  }
  fetchLeaderboard();
});
</script>