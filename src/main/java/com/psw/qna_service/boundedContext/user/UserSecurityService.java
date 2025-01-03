package com.psw.qna_service.boundedContext.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  // 시큐리티가 특정 회원을 username을 받았을 때
  // 그 username에 해당하는 회원정보를 얻는 수단.
  // 시큐리티는 siteUser 테이블이 존재하는지 모르기 때문에, 요정도까지는 구현 해줘야 함
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<SiteUser> _siteUser = userRepository.findByUsername(username);

    if (_siteUser.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }

    SiteUser siteUser = _siteUser.get();
    
    // 권한을 담을 빈 리스트를 생성
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(username)) {
      // UserRole.ADMIN.getValue() == "ROLE_ADMIN"

      authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue())); // 관리자 권한 부여
    } else {
      authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
    }
    return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
  }
}
