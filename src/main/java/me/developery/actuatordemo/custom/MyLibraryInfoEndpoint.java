package me.developery.actuatordemo.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Endpoint(id = "myLibraryInfo")
public class MyLibraryInfoEndpoint {
    @WriteOperation
    public void changeInfo(String name, boolean enableSomething) {
      log.info("name: {}, enableSomething: {}", name, enableSomething);
    }

    @ReadOperation
    public String getPath(@Selector String path) {
        log.info("path: {}", path);
        return "path: " + path;
    }

    @ReadOperation
    public String getPaths(@Selector(match = Selector.Match.ALL_REMAINING) String[] path) {
        log.info("path: {}", Arrays.asList(path));
        return "path: " + Arrays.asList(path);
    }

    @ReadOperation
    public List<LibraryInfo> getLibraryInfos(@Nullable String name, boolean includeVersion) {
        LibraryInfo libraryInfo1 = new LibraryInfo();
        libraryInfo1.setName("logback");
        libraryInfo1.setVersion("1.0.0");

        LibraryInfo libraryInfo2 = new LibraryInfo();
        libraryInfo2.setName("jackson");
        libraryInfo2.setVersion("2.0.0");

        List<LibraryInfo> libraryInfos = Arrays.asList(libraryInfo1, libraryInfo2);

        if (name != null) {
            libraryInfos = libraryInfos.stream()
                    .filter(lib -> lib.getName().equals(name))
                    .toList();
        }

        if (includeVersion == false) {
            libraryInfos = libraryInfos.stream()
                    .map(lib -> {
                        LibraryInfo temp = new LibraryInfo();
                        temp.setName(lib.getName());
                        return temp;
                    })
                    .toList();
        }

        return libraryInfos;
    }
}
