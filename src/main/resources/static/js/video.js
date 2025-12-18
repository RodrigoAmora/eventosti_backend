const video = document.getElementById("video");

function entrarEmFullscreen() {
    if (video.requestFullscreen) {
        video.requestFullscreen();
    } else if (video.webkitRequestFullscreen) {
        video.webkitRequestFullscreen();
    }
}

// Tenta travar em paisagem (Android funciona)
function travarPaisagem() {
    if (screen.orientation && screen.orientation.lock) {
        screen.orientation.lock("landscape").catch(() => {});
    }
}

window.addEventListener("load", () => {
    video.play().catch(() => {});
});

video.addEventListener("click", () => {
    entrarEmFullscreen();
    travarPaisagem();
});
