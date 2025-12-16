const video = document.getElementById("video");

function entrarEmFullscreen() {
    if (video.requestFullscreen) {
        video.requestFullscreen();
    } else if (video.webkitRequestFullscreen) {
        video.webkitRequestFullscreen();
    } else if (video.msRequestFullscreen) {
        video.msRequestFullscreen();
    }
}

window.addEventListener("load", () => {
    video.play().catch(() => {});
    entrarEmFullscreen();
});

document.addEventListener("click", entrarEmFullscreen);
