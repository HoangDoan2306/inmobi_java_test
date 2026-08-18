<template>
  <div class="min-h-screen bg-gradient-to-br from-yellow-50 to-orange-100">
    <div class="container mx-auto px-4 py-8 max-w-4xl">
      <!-- Header -->
      <div class="bg-white rounded-2xl shadow-lg p-6 mb-6">
        <h1 class="text-3xl font-bold text-gray-800 mb-2">Leaderboard</h1>
        <p class="text-gray-600">Top 10 players by score</p>
      </div>

      <!-- Leaderboard Content -->
      <div class="bg-white rounded-2xl shadow-lg p-6">
        <div v-if="loading" class="text-center py-8">
          <div class="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-yellow-600"></div>
          <p class="mt-4 text-gray-600">Loading leaderboard...</p>
        </div>

        <div v-else-if="error" class="text-center py-8">
          <div class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
            {{ error }}
          </div>
        </div>

        <div v-else-if="leaderboard.length === 0" class="text-center py-8">
          <div class="bg-gray-50 rounded-lg p-8">
            <h3 class="text-xl font-semibold text-gray-600 mb-2">No players yet</h3>
            <p class="text-gray-500">Be the first to join the game!</p>
          </div>
        </div>

        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-gray-50 border-b-2 border-gray-200">
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Rank</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Username</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Score</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
              <tr v-for="(entry, index) in leaderboard" :key="entry.username" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <div class="w-8 h-8 bg-yellow-100 rounded-full flex items-center justify-center">
                      <span class="text-yellow-800 font-semibold">{{ index + 1 }}</span>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm font-medium text-gray-900">{{ entry.username }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm font-semibold text-green-600">{{ entry.score }}</div>
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
          class="inline-block bg-blue-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors"
        >
          Back to Game
        </NuxtLink>
      </div>
    </div>
  </div>
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