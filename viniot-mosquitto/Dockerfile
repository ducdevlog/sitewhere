FROM golang:latest

MAINTAINER viniot Team <developer@viniot.net.vn>

ENV CGO_CFLAGS="-I/usr/local/include -fPIC"
ENV CGO_LDFLAGS="-shared"

RUN set -x && \
    apt-get update && apt-get install -y git curl build-essential libc-ares-dev uuid-dev libwebsockets-dev openssl && \
    rm -rf /var/lib/apt/lists/* && \
    cd / && \
    git clone https://github.com/eclipse/mosquitto.git && \
    cd mosquitto && \
    make -j "$(nproc)" WITH_WEBSOCKETS=yes WITH_DOCS=no && \
    make install WITH_WEBSOCKETS=yes WITH_DOCS=no && \
    groupadd mosquitto && \
    useradd -s /sbin/nologin mosquitto -g mosquitto -d /var/lib/mosquitto && \
    cd / && \
    git clone https://github.com/iegomez/mosquitto-go-auth.git && \
    cd mosquitto-go-auth && \
    make && \
    install -s -m755 go-auth.so /usr/local/lib/

COPY mosquitto.conf /mosquitto/config/mosquitto.conf
COPY mosquitto-go-auth.conf /mosquitto/config/conf.d/mosquitto-go-auth.conf

COPY run.sh /
RUN chmod +x /run.sh
ENTRYPOINT ["/run.sh"]

VOLUME ["/mosquitto/data", "/mosquitto/log"]

CMD ["/usr/local/sbin/mosquitto", "-c", "/mosquitto/config/mosquitto.conf"]