package com.example.backend.lib;

import com.example.backend.util.JWTGenerator;

import io.github.cdimascio.dotenv.Dotenv;

import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.util.List;

public class GithubClient {
    private GitHub github;
    private final String githubAppId = Dotenv.load().get("GITHUB_APP_ID");
    private final String githubAppPrivateKeyPath =
            Dotenv.load().get("GITHUB_APP_PRIVATE_FILE_PATH");
    private final Long githubAppInstallationId =
            Long.parseLong(Dotenv.load().get("GITHUB_APP_INSTALLATION_ID"));
    private final long ttlMillis = 600000;
    private JWTGenerator jwtGenerator = new JWTGenerator();

    public GithubClient() {
        try {
            this.github = buildGithub();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isPathExist(String repo) {
        try {
            return github.getRepository(repo) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDefaultBranch(String repo) {
        try {
            return github.getRepository(repo).getDefaultBranch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllFiles(String repo) {
        try {
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
            return github.getRepository(repo).getFileContent(path).getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GitHub buildGithub() throws Exception {
        String jwtToken = jwtGenerator.createJWT(githubAppPrivateKeyPath, githubAppId, ttlMillis);
        GitHub gitHubApp = new GitHubBuilder().withJwtToken(jwtToken).build();
        GHAppInstallation appInstallation =
                gitHubApp.getApp().getInstallationById(githubAppInstallationId);
        GHAppInstallationToken appInstallationToken = appInstallation.createToken().create();
        return new GitHubBuilder()
                .withAppInstallationToken(appInstallationToken.getToken())
                .build();
    }
}
