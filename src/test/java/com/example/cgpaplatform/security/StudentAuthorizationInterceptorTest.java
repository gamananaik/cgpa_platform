package com.example.cgpaplatform.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.cgpaplatform.entity.User;
import com.example.cgpaplatform.entity.student;
import com.example.cgpaplatform.repository.StudentRepository;

class StudentAuthorizationInterceptorTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentAuthorizationInterceptor interceptor;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        interceptor = new StudentAuthorizationInterceptor(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
        mocks.close();
    }

    @Test
    void allowsStudentToAccessTheirOwnProfile() {
        when(studentRepository.findById(17L))
                .thenReturn(Optional.of(studentOwnedBy("alice")));
        authenticate("alice", "ROLE_STUDENT");

        boolean allowed = interceptor.preHandle(
                request("/api/students/17/analytics/sgpa"),
                new MockHttpServletResponse(),
                new Object());

        assertTrue(allowed);
    }

    @Test
    void deniesStudentAccessToAnotherStudentsProfile() {
        when(studentRepository.findById(17L))
                .thenReturn(Optional.of(studentOwnedBy("alice")));
        authenticate("bob", "ROLE_STUDENT");

        assertThrows(AccessDeniedException.class, () -> interceptor.preHandle(
                request("/api/students/17"),
                new MockHttpServletResponse(),
                new Object()));
    }

    @Test
    void allowsAdminToAccessAnyStudentProfile() {
        authenticate("admin", "ROLE_ADMIN");

        assertDoesNotThrow(() -> interceptor.preHandle(
                request("/api/students/17"),
                new MockHttpServletResponse(),
                new Object()));
        verify(studentRepository, org.mockito.Mockito.never()).findById(17L);
    }

    private void authenticate(String username, String role) {
        var authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private MockHttpServletRequest request(String path) {
        return new MockHttpServletRequest("GET", path);
    }

    private student studentOwnedBy(String username) {
        User user = new User();
        user.setUsername(username);

        student profile = new student();
        profile.setUser(user);
        return profile;
    }
}