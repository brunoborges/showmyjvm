import { test, expect } from '@playwright/test';

/**
 * E2E tests for ShowMyJVM implementations
 * 
 * These tests verify that each framework implementation exposes the required endpoints:
 * - /jvm/inspect (plain text)
 * - /jvm/inspect.json (JSON format)
 * - /index.html (shared web UI, packaged in the web-ui module)
 * 
 */

test.describe('ShowMyJVM endpoints', () => {
    
  test('should respond to /jvm/inspect with plain text', async ({ request }) => {
    const response = await request.get('/jvm/inspect');
    
    expect(response.ok()).toBeTruthy();
    expect(response.status()).toBe(200);
    
    const contentType = response.headers()['content-type'];
    expect(contentType).toContain('text/plain');
    
    const body = await response.text();
    expect(body).toBeTruthy();
    expect(body.length).toBeGreaterThan(0);
    
    // Verify it contains JVM-related information
    expect(body).toMatch(/java|jvm|version/i);
  });

  test('should respond to /jvm/inspect.json with JSON', async ({ request }) => {
    const response = await request.get('/jvm/inspect.json');
    
    expect(response.ok()).toBeTruthy();
    expect(response.status()).toBe(200);
    
    const contentType = response.headers()['content-type'];
    expect(contentType).toContain('application/json');
    
    const body = await response.json();
    expect(body).toBeTruthy();
    expect(typeof body).toBe('object');
    
    // Verify the JSON structure contains expected JVM details
    // These fields are from the JVMDetails class in the core module
    expect(body).toHaveProperty('vmVendor');
    expect(body).toHaveProperty('vmVersion');
    expect(body).toHaveProperty('osName');
    expect(body).toHaveProperty('osVersion');
  });

});

test.describe('ShowMyJVM shared web UI', () => {

  test('should serve /index.html as HTML', async ({ request }) => {
    const response = await request.get('/index.html');

    expect(response.ok()).toBeTruthy();
    expect(response.headers()['content-type']).toContain('text/html');
  });

  test('should render JVM details fetched from the REST API', async ({ page }) => {
    await page.goto('/index.html');

    await expect(page.locator('#status')).toContainText('Updated');
    await expect(page.locator('#runtime')).toContainText(/VM version/);
    await expect(page.locator('#runtime dd').first()).not.toHaveText('—');
    await expect(page.locator('#os')).toContainText(/Architecture/);
    await expect(page.locator('#pools tr').first()).toBeVisible();
  });

});
