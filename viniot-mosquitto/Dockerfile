FROM eclipse-mosquitto as packager

MAINTAINER viniot Team <developer@viniot.net.vn>

ENV PATH=/usr/local/bin:/usr/local/sbin:$PATH

EXPOSE 1883
EXPOSE 9883

VOLUME ["/var/lib/mosquitto", "/etc/mosquitto", "/etc/mosquitto.d"]

# Build and install Coturn
RUN apk update \
 && apk upgrade \
 && apk add --no-cache --virtual buildDeps git cmake build-base c-ares-dev util-linux-dev curl-dev ca-certificates \
 && update-ca-certificates \
 # Install Coturn dependencies
 && apk add --no-cache libevent snappy zlib curl \
 # Install tools for building
 && apk add --no-cache --virtual .tool-deps coreutils autoconf g++ libtool make  cmake \
 # Install mongo-c-driver build dependencies
 && apk add --no-cache --virtual .build-deps  linux-headers libevent-dev snappy-dev zlib-dev \
 # Download and prepare mongo-c-driver sources
 && curl -fL -o /tmp/mongo-c-driver.tar.gz https://github.com/mongodb/mongo-c-driver/archive/1.14.0.tar.gz \
 && tar -xzf /tmp/mongo-c-driver.tar.gz -C /tmp/ \
 && cd /tmp/mongo-c-driver-* \
 # Build mongo-c-driver from sources
 # https://git.alpinelinux.org/aports/tree/non-free/mongo-c-driver/APKBUILD
 && mkdir -p /tmp/build/mongo-c-driver/ \
 && cd /tmp/build/mongo-c-driver/ \
 && cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=/usr -DCMAKE_INSTALL_LIBDIR=lib -DENABLE_BSON:STRING=ON -DENABLE_MONGOC:BOOL=ON -DENABLE_SSL:STRING=OPENSSL \
          -DENABLE_AUTOMATIC_INIT_AND_CLEANUP:BOOL=OFF -DENABLE_MAN_PAGES:BOOL=OFF -DENABLE_TESTS:BOOL=ON -DENABLE_EXAMPLES:BOOL=OFF -DCMAKE_SKIP_RPATH=ON \
           /tmp/mongo-c-driver-* \
           \
 && make \
 # Check mongo-c-driver build
 && MONGOC_TEST_SKIP_MOCK=on MONGOC_TEST_SKIP_SLOW=on MONGOC_TEST_SKIP_LIVE=on make check \
 # Install mongo-c-driver
 && make install \
 && cd / \
 && git clone git://github.com/jpmens/mosquitto-auth-plug.git  \
 && cd mosquitto-auth-plug  \
 && cp config.mk.in config.mk  \
 && sed -i "s/BACKEND_MYSQL ?= yes/BACKEND_MYSQL ?= no/" config.mk  \
 && sed -i "s/BACKEND_REDIS ?= yes/BACKEND_REDIS ?= no/" config.mk  \
 && sed -i "s/BACKEND_HTTP ?= yes/BACKEND_HTTP ?= no/" config.mk   \
 && sed -i "s/BACKEND_POSTGRES ?= yes/BACKEND_POSTGRES ?= no/" config.mk   \
 && sed -i "s/BACKEND_JWT ?= yes/BACKEND_JWT ?= no/" config.mk  \
 && sed -i "s/BACKEND_MONGO ?= no/BACKEND_MONGO ?= yes/" config.mk  \
 && sed -i "s/BACKEND_FILES ?= yes/BACKEND_FILES ?= no/" config.mk  \
 && sed -i "s/MOSQUITTO_SRC =/MOSQUITTO_SRC = ..\//" config.mk \
 && sed -i "s/EVP_MD_CTX_new/EVP_MD_CTX_create/g" cache.c  \
 && sed -i "s/EVP_MD_CTX_free/EVP_MD_CTX_destroy/g" cache.c  \
 && make \
 && install -s -m755 auth-plug.so /usr/local/lib/ \
 && install -s -m755 np /usr/local/bin/ \

 && apk del .tool-deps .build-deps \
 && rm -rf /var/cache/apk/* /tmp/*

COPY run.sh /
RUN chmod +x /run.sh

COPY mosquitto.conf /mosquitto/config/mosquitto.conf

VOLUME ["/mosquitto/data", "/mosquitto/log"]

ADD mosquitto.conf /etc/mosquitto/mosquitto.conf
ENTRYPOINT ["/run.sh"]
CMD ["mosquitto"]