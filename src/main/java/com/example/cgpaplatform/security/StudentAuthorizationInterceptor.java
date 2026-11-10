package com.example.cgpaplatform.security;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.cgpaplatform.entity.student;
import com.example.cgpaplatform.repository.StudentRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class StudentAuthorizationInterceptor implements HandlerInterceptor {

    private final StudentRepository studentRepository;

    public StudentAuthorizationInterceptor(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        Long studentId = extractStudentId(request);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (studentId == null || authentication == null
                || hasAdminRole(authentication)) {
            return true;
        }

        Optional<student> requestedStudent = studentRepository.findById(studentId);
        if (requestedStudent.isEmpty()) {
            return true;
        }

        student profile = requestedStudent.get();
        if (profile.getUser() != null
                && authentication.getName().equals(profile.getUser().getUsername())) {
            return true;
        }

        throw new AccessDeniedException(
                "You are not allowed to access this student's data.");
    }

    private Long extractStudentId(HttpServletRequest request) {
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());
        String[] segments = path.split("/");

        if (segments.length < 4
                || !"api".equals(segments[1])
                || !"students".equals(segments[2])) {
            return null;
        }

        try {
            return Long.valueOf(segments[3]);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}