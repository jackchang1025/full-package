FROM php:8.2-fpm-alpine

ENV TZ=Asia/Shanghai
RUN apk add --no-cache tzdata \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

RUN apk add --no-cache \
    libpng-dev \
    libjpeg-turbo-dev \
    freetype-dev \
    libzip-dev \
    curl-dev \
    oniguruma-dev \
    libxml2-dev \
    unzip \
    git \
    openjdk17-jre-headless \
    bash \
    rsync

# 安装 glibc 兼容层 (Android build-tools 需要)
RUN apk add --no-cache gcompat libstdc++

RUN docker-php-ext-configure gd --with-freetype --with-jpeg \
    && docker-php-ext-install -j$(nproc) \
    pdo_mysql \
    mysqli \
    mbstring \
    gd \
    curl \
    zip \
    fileinfo \
    xml \
    opcache

ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH="${PATH}:${ANDROID_SDK_ROOT}/build-tools/34.0.0"

RUN mkdir -p ${ANDROID_SDK_ROOT}/build-tools && \
    cd /tmp && \
    wget -q https://dl.google.com/android/repository/build-tools_r34-linux.zip && \
    unzip -q build-tools_r34-linux.zip -d ${ANDROID_SDK_ROOT}/build-tools && \
    mv ${ANDROID_SDK_ROOT}/build-tools/android-14 ${ANDROID_SDK_ROOT}/build-tools/34.0.0 && \
    chmod +x ${ANDROID_SDK_ROOT}/build-tools/34.0.0/* && \
    rm -f /tmp/build-tools_r34-linux.zip

RUN echo "date.timezone = Asia/Shanghai" > /usr/local/etc/php/conf.d/timezone.ini \
    && echo "upload_max_filesize = 50M" > /usr/local/etc/php/conf.d/uploads.ini \
    && echo "post_max_size = 50M" >> /usr/local/etc/php/conf.d/uploads.ini \
    && echo "max_execution_time = 300" >> /usr/local/etc/php/conf.d/uploads.ini \
    && echo "memory_limit = 512M" >> /usr/local/etc/php/conf.d/uploads.ini \
    && echo "display_errors = On" >> /usr/local/etc/php/conf.d/uploads.ini \
    && echo "error_reporting = E_ALL" >> /usr/local/etc/php/conf.d/uploads.ini

WORKDIR /var/www/html

EXPOSE 9000

RUN mkdir -p /var/www/html/src/private/logs \
    && chown -R www-data:www-data /var/www/html/src/private/logs \
    && chmod -R 755 /var/www/html/src/private/logs

CMD ["php-fpm"]
