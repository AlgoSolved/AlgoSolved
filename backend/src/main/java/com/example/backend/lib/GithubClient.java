package com.example.backend.lib;

import com.example.backend.util.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GithubClient {

    private final long ttlMillis = 600000;

    @Value("${github.app.id}")
    private String githubAppId;

    @Value("${github.app.privateKey}")
    private String githubAppPrivateKey;

    @Value("${github.app.installationId}")
    private Long githubAppInstallationId;

    @Value("${github.app.maxFileSize}")
    private Long githubAppMaxFileSize;

    public Boolean isPathExist(String repo) {
        try {
            GitHub github = buildGithub();
            return github.getRepository(repo) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDefaultBranch(String repo) {
        try {
            GitHub github = buildGithub();
            return github.getRepository(repo).getDefaultBranch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllFiles(String repo) {
        try {
            GitHub github = buildGithub();
            String defaultBranch = getDefaultBranch(repo);
            List<GHTreeEntry> ghTreeEntries =
                    github.getRepository(repo).getTreeRecursive(defaultBranch, 1).getTree();
            List<String> result = ghTreeEntries.stream().map(GHTreeEntry::getPath).toList();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getContent(String repo, String path) {
        try {
            GitHub github = buildGithub();
            GHContent content = github.getRepository(repo).getFileContent(path);
            if (content.getSize() > githubAppMaxFileSize) {
                log.error("{} file size is over max file size", path);
                return null;
            }
            return content.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GitHub buildGithub() throws Exception {
        String jwtToken = JWTGenerator.createJWT(githubAppPrivateKey, githubAppId, ttlMillis);
        GitHub gitHubApp = new GitHubBuilder().withJwtToken(jwtToken).build();
        GHAppInstallation appInstallation =
                gitHubApp.getApp().getInstallationById(githubAppInstallationId);
        GHAppInstallationToken appInstallationToken = appInstallation.createToken().create();
        return new GitHubBuilder()
                .withAppInstallationToken(appInstallationToken.getToken())
                .build();
    }
}
