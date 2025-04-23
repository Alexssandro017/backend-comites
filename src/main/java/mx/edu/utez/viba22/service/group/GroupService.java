package mx.edu.utez.viba22.service.group;

import mx.edu.utez.viba22.model.group.Group;
import mx.edu.utez.viba22.model.group.GroupRepository;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(Group group) {
        // Recupera el User desde la base de datos
        User groupAdmin = userRepository.findById(group.getGroupAdmin().getIdUser())
                .orElseThrow(() -> new RuntimeException("User not found with id " + group.getGroupAdmin().getIdUser()));

        // Asigna el User recuperado al Group
        group.setGroupAdmin(groupAdmin);

        // Guarda el Group
        return groupRepository.save(group);
    }

    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    public Group updateGroup(Long id, Group groupDetails) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id " + id));
        group.setName(groupDetails.getName());
        group.setMunicipality(groupDetails.getMunicipality());
        group.setColony(groupDetails.getColony());
        group.setGroupAdmin(groupDetails.getGroupAdmin());
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}