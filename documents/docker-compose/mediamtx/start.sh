docker compose  up -d &&
docker exec -it mediamtx sh -c 'ffmpeg -re -stream_loop -1 -i /data/input/01.mp4 -c copy -f rtsp rtsp://localhost:8554/01'