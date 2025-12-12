import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: 'html',
  use: {
    baseURL: 'http://localhost:8080',
    trace: 'on-first-retry',
  },

  projects: [
    {
      name: 'helidon',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'helidon-mp',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'javalin',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'micronaut',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'quarkus',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'ratpack',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'sparkjava',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'spring-boot',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'tomcat',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
