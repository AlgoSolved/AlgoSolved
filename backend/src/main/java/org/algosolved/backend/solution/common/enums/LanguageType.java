package org.algosolved.backend.solution.common.enums;

import lombok.Getter;

public enum LanguageType {
    // TODO: 파일 확장명으로 python 과 python3 을 구분해야 됩니다.
    c("C", ".c"),
    cpp("C++", ".cpp"),
    java("Java", ".java"),
    python("Python", ".py"),
    python3("Python3", ".py"),
    javascript("JavaScript", ".js"),
    typescript("TypeScript", ".ts"),
    kotlin("Kotlin", ".kt"),
    swift("Swift", ".swift"),
    go("Go", ".go"),
    rust("Rust", ".rs"),
    php("PHP", ".php"),
    ruby("Ruby", ".rb"),
    perl("Perl", ".pl"),
    r("R", ".r");

    @Getter private final String name;
    @Getter private final String extension;

    LanguageType(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public static boolean containsExtension(String fileName) {
        for (LanguageType type : LanguageType.values()) {
            if (fileName.endsWith(type.getExtension())) {
                return true;
            }
        }
        return false;
    }

    public static LanguageType getLanguageType(String fileName) {
        for (LanguageType type : LanguageType.values()) {
            if (fileName.endsWith(type.getExtension())) {
                return type;
            }
        }
        return null;
    }
}
