#!/usr/bin/env bash

mkdir bookshelf
cd bookshelf

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=book-service \
--package-name=com.jin.bookshelf.core.book \
--groupId=com.jin.bookshelf.core.book \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
book-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=accounting-service \
--package-name=com.jin.bookshelf.core.accounting \
--groupId=com.jin.bookshelf.core.accounting \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
accounting-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=portal-service \
--package-name=com.jin.bookshelf.core.portal \
--groupId=com.jin.bookshelf.core.portal \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
portal-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=book-composite-service \
--package-name=com.jin.bookshelf.composite.book \
--groupId=com.jin.bookshelf.composite.book \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
book-composite-service

cd ..
