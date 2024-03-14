package com.example.backend.lib;

import com.example.backend.util.JWTGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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

    @Value("${github.oauth.client.id}")
    private String githubAppClientId;

    @Value("${github.oauth.client.secret}")
    private String githubAppClientSecret;

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

    public Boolean revokeOauthToken(String token) {
        // github-api 에 구현된게 없어서 직접 구현하였습니다
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("access_token", token);
            String auth =
                    Base64.getEncoder()
                            .encodeToString(
                                    String.format("%s:%s", githubAppClientId, githubAppClientSecret)
                                            .getBytes());

            ObjectMapper mapper = new ObjectMapper();

            byte[] paramBytes = mapper.writeValueAsString(params).getBytes(StandardCharsets.UTF_8);

            HttpURLConnection connection = getHttpURLConnection(auth);
            connection.getOutputStream().write(paramBytes);

            int status = connection.getResponseCode();

            connection.disconnect();

            return status == HttpStatus.NO_CONTENT.value();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private HttpURLConnection getHttpURLConnection(String auth) throws IOException {
        URL url =
                new URL(
                        String.format(
                                "https://api.github.com/applications/%s/grant", githubAppClientId));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.addRequestProperty("Authorization", "Basic " + auth);
        connection.addRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.addRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded charset=utf-8");
        connection.setDoOutput(true);
        return connection;
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
