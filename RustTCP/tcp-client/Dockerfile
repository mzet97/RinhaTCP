FROM rustlang/rust:nightly-alpine AS builder
WORKDIR /app
RUN apk add --no-cache musl-dev
COPY Cargo.toml .
COPY src ./src
RUN cargo build --release

FROM alpine:3.19
WORKDIR /app
RUN apk add --no-cache libgcc
COPY --from=builder /app/target/release/tcp-client ./tcp-client
CMD ["./tcp-client"]