# Overview
A Java program I developed to function as a command-line utility for PowerShell or any terminal within an IDE.

## Purpose

The primary purpose of this software is to automate the tedious task of manually copying and pasting multiple files into an LLM. By gathering the contents of multiple files into a single output file, this tool saves time and reduces the likelihood of errors. Additionally, it serves as a practical example of how Java can be used to create efficient, command-line-based utilities.

I wrote this software because I wanted to simplify my workflow and explore Java's capabilities in handling file operations and command-line interactions. It also provided an opportunity to deepen my understanding of Java's syntax and best practices.

## Software Demo Video

[Software Demo Video](https://youtu.be/81OHKosPT60)

# Development Environment

To develop this software, I used the following tools and environment:

- **IDE**: IntelliJ IDEA
- **Java Version**: 23.0.2
- **JRE Version**: 23.0.2+7-58
- **JVM Version**: 23.0.2+7-58

The programming language used is Java, and no external libraries were required for this project. The tool leverages Java's built-in libraries for file handling, command-line argument parsing, and basic I/O operations.

# Useful Websites

During the development of this project, I found the following websites particularly helpful:

- [Java Programming Masterclass](https://gale.udemy.com/course/java-the-complete-java-developer-course/)
- [Oracle Java Documentation](https://docs.oracle.com/en/java/)

# Future Work

While the current version of the software meets the basic requirements, there are several areas for improvement and additional features that could be implemented in the future:

1. **Recursive Directory Search**: Add an option to recursively search subdirectories and include their files in the output.
2. **Duplicate Code Handling**: Fix the bug where duplicate code is sometimes saved in the output file.
3. **LLM API Integration**: Implement functionality to directly send the output.txt file to an LLM API, eliminating the need for manual copying and pasting.
