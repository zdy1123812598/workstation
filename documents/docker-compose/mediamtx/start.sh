docker compose  up -d &&
#docker exec -it mediamtx sh -c 'ffmpeg -re -stream_loop -1 -i /data/input/01.mp4 -c copy -f rtsp rtsp://10.100.1.112:8554/01' &&
#docker exec -it mediamtx sh -c 'ffmpeg -re -stream_loop -1 -i /data/input/01.mp4 -c copy -f rtmp rtmp://10.100.1.112:11935/1/1?sign=41db35390ddad33f83944f44b8b75ded' &&
docker exec -it mediamtx sh -c 'ffmpeg -re -stream_loop -1 -i /data/input/142.mp4 -c copy -f rtsp://admin:qazwsx12@10.101.2.142/cam/realmonitor?channel=1&subtype=0' &&