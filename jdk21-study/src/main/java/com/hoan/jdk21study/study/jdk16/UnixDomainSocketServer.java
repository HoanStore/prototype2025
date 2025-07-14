package com.hoan.jdk21study.study.jdk16;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Files;
import java.net.UnixDomainSocketAddress;

public class UnixDomainSocketServer {
    public static void main(String[] args) throws IOException {
        Path socketPath = Path.of("/tmp/mysocket");
        Files.deleteIfExists(socketPath); // 기존 파일 삭제

        SocketAddress address = UnixDomainSocketAddress.of(socketPath);
        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(address);
            System.out.println("서버 대기 중...");

            try (SocketChannel client = server.accept()) {
                ByteBuffer buffer = ByteBuffer.allocate(256);
                client.read(buffer);
                buffer.flip();
                System.out.println("수신 메시지: " + new String(buffer.array(), 0, buffer.limit()));
            }
        }
    }
}

