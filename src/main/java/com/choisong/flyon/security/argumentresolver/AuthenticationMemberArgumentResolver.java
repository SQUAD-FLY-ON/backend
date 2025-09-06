package com.choisong.flyon.security.argumentresolver;

import com.choisong.flyon.security.annotation.AuthenticationMember;
import com.choisong.flyon.security.principal.MemberPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationMember.class)
            && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory)
        throws Exception {
        final MemberPrincipal principal = (MemberPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return Long.parseLong(principal.getName());
    }
}
