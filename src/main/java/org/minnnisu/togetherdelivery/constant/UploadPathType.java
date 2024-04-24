package org.minnnisu.togetherdelivery.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadPathType {
    POST_IMAGE("src/main/resources/static/images/post");

    private final String Path;
}
