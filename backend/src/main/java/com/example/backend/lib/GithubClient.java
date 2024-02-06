package com.example.backend.lib;

import com.example.backend.util.JWTGenerator;
import java.util.List;
import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubClient {
    @Value("${github.app.id}")
    private String githubAppId;
    @Value("${github.app.privateFilePath}")
    private String githubAppPrivateKeyPath;
    @Value("${github.app.installationId}")
    private Long githubAppInstallationId;
    private final long ttlMillis = 600000;

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
            return github.getRepository(repo).getFileContent(path).getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GitHub buildGithub() throws Exception {
        String jwtToken = JWTGenerator.createJWT(githubAppPrivateKeyPath, githubAppId, ttlMillis);
        GitHub gitHubApp = new GitHubBuilder().withJwtToken(jwtToken).build();
        GHAppInstallation appInstallation =
                gitHubApp.getApp().getInstallationById(githubAppInstallationId);
        GHAppInstallationToken appInstallationToken = appInstallation.createToken().create();
        return new GitHubBuilder()
                .withAppInstallationToken(appInstallationToken.getToken())
                .build();
    }
}
