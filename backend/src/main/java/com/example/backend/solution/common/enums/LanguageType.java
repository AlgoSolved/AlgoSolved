package com.example.backend.solution.common.enums;

import lombok.Getter;

public enum LanguageType {
    // 표시 언어, DB 저장 언어, 파일 확장자
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
