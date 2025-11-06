import { defineConfig, loadEnv } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

export default defineConfig(({ mode }) => {
  // Load env variables based on the current mode (development/production)
  const env = loadEnv(mode, process.cwd(), '')

  return {
    plugins: [svelte()],
    server: {
      proxy: {
        '/api': {
          target: env.API_URL,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
  }
})