FROM gcr.io/distroless/base
COPY build/native/nativeCompile/golden-raspberry-awards-api app
ENTRYPOINT ["/app"]