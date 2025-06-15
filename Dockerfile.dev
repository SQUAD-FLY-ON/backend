FROM gradle:8.7-jdk17

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app
COPY . /app

CMD ["gradle", "bootRun", "-Duser.timezone=Asia/Seoul"]