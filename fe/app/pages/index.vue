<template>
  <div class="min-h-screen bg-[#f5f5f5] flex items-center justify-center px-4 py-10">
    <div class="w-full max-w-5xl">
      <div class="relative overflow-hidden rounded-[32px] bg-black shadow-[0_30px_100px_rgba(0,0,0,0.18)]">

        <!-- Glow -->
        <div class="absolute -top-40 -right-40 w-96 h-96 bg-[#863cff]/30 rounded-full blur-[100px]"></div>
        <div class="absolute -bottom-40 -left-40 w-96 h-96 bg-[#7040ff]/20 rounded-full blur-[100px]"></div>

        <!-- Top gradient -->
        <div class="absolute top-0 left-0 right-0 h-1.5 bg-gradient-to-r from-[#7040ff] via-[#863cff] to-[#a33cff]"></div>

        <div class="relative z-10 px-6 py-10 sm:px-12 sm:py-14 lg:px-20 lg:py-16">

          <!-- Logo -->
          <div class="flex justify-center mb-8">
            <div class="w-16 h-16 rounded-[20px] bg-white flex items-center justify-center shadow-[0_10px_40px_rgba(134,60,255,0.25)]">
              <span class="w-9 h-9">
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
          </div>

          <!-- Hero -->
          <div class="text-center max-w-3xl mx-auto">
            <div class="inline-flex items-center gap-2 px-3.5 py-1.5 mb-6 rounded-full bg-white/10 border border-white/10 text-white/70 text-xs font-medium">
              <span class="w-1.5 h-1.5 rounded-full bg-[#a33cff] shadow-[0_0_10px_#a33cff]"></span>
              THE ULTIMATE NUMBER CHALLENGE
            </div>

            <h1 class="text-4xl sm:text-5xl lg:text-7xl font-bold tracking-tight text-white leading-[1.05] mb-6">
              Guess the Number.
              <span class="block bg-gradient-to-r from-[#7040ff] via-[#863cff] to-[#a33cff] bg-clip-text text-transparent">
                Beat the Odds.
              </span>
            </h1>

            <p class="text-base sm:text-lg text-white/50 max-w-xl mx-auto leading-relaxed mb-10">
              Test your intuition, make your guess, and see how quickly you can
              discover the hidden number.
            </p>

            <!-- CTA -->
            <div class="flex flex-col sm:flex-row items-center justify-center gap-3">
              <NuxtLink
                to="/login"
                class="group w-full sm:w-auto inline-flex items-center justify-center gap-2 bg-white text-black px-7 py-3.5 rounded-xl font-semibold text-sm hover:bg-gradient-to-r hover:from-[#7040ff] hover:via-[#863cff] hover:to-[#a33cff] hover:text-white transition-all duration-300 shadow-[0_10px_40px_rgba(0,0,0,0.2)]"
              >
                Start Playing
                <svg
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
              </NuxtLink>

              <NuxtLink
                to="/register"
                class="w-full sm:w-auto inline-flex items-center justify-center px-7 py-3.5 rounded-xl border border-white/10 text-white/70 font-semibold text-sm hover:bg-white/5 hover:text-white transition-all"
              >
                Create Account
              </NuxtLink>
            </div>
          </div>

          <!-- How to play -->
          <div class="mt-10 pt-8 border-t border-white/[0.08]">
            <div class="grid grid-cols-1 sm:grid-cols-3 gap-6 text-center sm:text-left">

              <div class="flex flex-col sm:flex-row items-center sm:items-start gap-3">
                <div class="w-9 h-9 shrink-0 rounded-xl bg-white/10 flex items-center justify-center text-white text-sm font-bold">
                  01
                </div>
                <div>
                  <h3 class="text-sm font-semibold text-white mb-1">Make a Guess</h3>
                  <p class="text-xs text-white/35">Choose your number wisely.</p>
                </div>
              </div>

              <div class="flex flex-col sm:flex-row items-center sm:items-start gap-3">
                <div class="w-9 h-9 shrink-0 rounded-xl bg-white/10 flex items-center justify-center text-white text-sm font-bold">
                  02
                </div>
                <div>
                  <h3 class="text-sm font-semibold text-white mb-1">Get a Hint</h3>
                  <p class="text-xs text-white/35">Use every clue to your advantage.</p>
                </div>
              </div>

              <div class="flex flex-col sm:flex-row items-center sm:items-start gap-3">
                <div class="w-9 h-9 shrink-0 rounded-xl bg-gradient-to-br from-[#7040ff] to-[#a33cff] flex items-center justify-center text-white text-sm font-bold">
                  03
                </div>
                <div>
                  <h3 class="text-sm font-semibold text-white mb-1">Beat the Game</h3>
                  <p class="text-xs text-white/35">Find the number in fewer turns.</p>
                </div>
              </div>

            </div>
          </div>

        </div>
      </div>

      <!-- Footer -->
      <div class="text-center mt-6">
        <p class="text-xs text-black/30">
          Number Guessing Game
          <span class="mx-1">•</span>
          Think smart. Guess better.
        </p>
      </div>
    </div>
  </div>
</template>

