docker rmi -f app:v1.0.0
docker-compose -f docker-compose.yml down
docker build -f Dockerfile -t app:v1.0.0 .
docker-compose -f docker-compose.yml up -d
