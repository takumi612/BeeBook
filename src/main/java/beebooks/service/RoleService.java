package beebooks.service;

import beebooks.entities.Role;
import beebooks.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role,Integer> {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
		super(repository);
        this.repository = repository;
    }


    public Role loadRoleByRoleName(String roleName) {
		return repository.findByName(roleName);
	}
}
