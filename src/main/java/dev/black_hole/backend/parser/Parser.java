package dev.black_hole.backend.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.xml.sax.SAXException;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.github.luben.zstd.ZstdInputStream;

public class Parser {
    // zstd -> tar -> plist -> Map<String, NSObject>
    public static Optional<NSDictionary> toMap(File file) throws IOException,
            PropertyListFormatException, ParseException,
            ParserConfigurationException, SAXException {
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStream zstd = new ZstdInputStream(fileInputStream);
                TarArchiveInputStream tar = new TarArchiveInputStream(zstd)) {
            TarArchiveEntry entry = tar.getNextEntry();
            if (entry == null) {
                return Optional.empty();
            }
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(tar);
            return Optional.of(rootDict);
        }
    }
}