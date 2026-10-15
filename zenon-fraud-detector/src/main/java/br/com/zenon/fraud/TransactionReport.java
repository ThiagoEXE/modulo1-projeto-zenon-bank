package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TransactionReport {

    public Stream<String> lazyFileCSV (String fileName) {
        Path path = Paths.get(fileName);

        try {
            Stream<String> lines = Files.lines(path);
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }
}
