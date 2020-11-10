package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int add(Credential credential, int userId) {
        credential.setUserId(userId);
        return credentialMapper.add(credential);
    }

    public List<Credential> getAllCredentialsForUser(int userId) {
        return credentialMapper.getAllCredentialsForUser(userId);
    }

    public int getUserId(int credentialId) {
        Credential credential = credentialMapper.getById(credentialId);
        return credential != null ? credential.getUserId() : -1;
    }

    public boolean delete(int id) {
        Credential credential = credentialMapper.getById(id);
        if (credential != null) {
            credentialMapper.delete(id);
            return true;
        }

        return false;
    }

    public void update(Credential credential) {
        credentialMapper.update(credential);
    }

    public Credential getById(int id) {
        return credentialMapper.getById(id);
    }

    public boolean exists(int id) {
        return credentialMapper.getById(id) != null;
    }
}
