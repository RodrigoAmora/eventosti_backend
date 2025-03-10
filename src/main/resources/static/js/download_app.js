var userAgentBrowser = navigator.userAgent;

if (userAgentBrowser.includes("Android")) {
  window.location.href = "https://play.google.com/store/apps/details?id=br.com.rodrigoamora.eventosti"
}
if (userAgentBrowser.includes("iPad") ||
    userAgentBrowser.includes("iPhone")) {
  window.location.href = "https://apps.apple.com/us/app/eventos-ti/id6630363975"
}