FROM openjdk:8-jdk-stretch


RUN apt-get update && \
    apt-get install -y git gradle && \
    git clone https://github.com/voldemort/voldemort.git && \
    cd voldemort && \
    ./gradlew clean build -x test

ADD https://github.com/voldemort/voldemort/archive/release-1.10.25-cutoff.zip .

COPY documents.xml /voldemort/config/single_node_cluster/config/STORES

ENV VOLDEMORT_HOME ./voldemort/
RUN export VOLDEMORT_HOME

EXPOSE 6666

CMD ["voldemort/bin/voldemort-server.sh", "voldemort/config/single_node_cluster"]


