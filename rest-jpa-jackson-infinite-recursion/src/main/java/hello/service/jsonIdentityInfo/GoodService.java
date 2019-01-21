package hello.service.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Good;

import java.util.List;

public interface GoodService {

    Good get(Long id);

    List<Good> getAll();
}
