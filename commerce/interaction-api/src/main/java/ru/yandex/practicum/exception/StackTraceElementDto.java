package ru.yandex.practicum.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StackTraceElementDto {
     String classLoaderName;
     String moduleName;
     String moduleVersion;
     String methodName;
     String fileName;
     int lineNumber;
     String className;
     boolean nativeMethod;
}