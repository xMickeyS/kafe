/* 6410402104 Pichaya Sena */
package ku.cs.kafe.service;

import ku.cs.kafe.entity.Member;
import ku.cs.kafe.model.SignupRequest;
import ku.cs.kafe.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// SignupService ประมวลผล user ก่อน save เข้าไปใน database และหลังจากค้นคืนมาจาก database
@Service
public class SignupService {

    @Autowired
    private MemberRepository repository;

    // ดยคลาส SignupService นี้ จะทำการ hash password ก่อน save ลง database โดยใช้ PasswordEncoder object ที่เราจะสร้าง Bean ไว้ใน security config
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isUsernameAvailable(String username) {
        return repository.findByUsername(username) == null;
    }

    public void createUser(SignupRequest user) {
        Member record = modelMapper.map(user, Member.class);
        record.setRole("ROLE_USER");

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        repository.save(record);
    }


    public Member getUser(String username) {
        return repository.findByUsername(username);
    }
}
