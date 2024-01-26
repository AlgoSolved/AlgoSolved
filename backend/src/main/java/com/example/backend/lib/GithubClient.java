package com.example.backend.lib;

import io.github.cdimascio.dotenv.Dotenv;

import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.util.List;

public class GithubClient {
    private GitHub github;

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

    public List<String> getAllFiles(String repo) {
        try {
            List<GHTreeEntry> ghTreeEntries =
                    github.getRepository(repo).getTreeRecursive("master", 1).getTree();
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
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("GITHUB_TOKEN");
        return new GitHubBuilder().withOAuthToken(token).build();
    }

    // JWT token을 생성하는 메소드입니다
    // 이후 personal token 이 아닌 Github App을 이용하여 API를 호출하도록 수정해야 합니다
    //    private GitHub buildGithub() throws Exception {
    //        String jwtToken = getJwtToken();
    //        return new GitHubBuilder().withJwtToken(jwtToken).build();
    //    }
    //
    //    private String getJwtToken() throws Exception {
    //        String appId = System.getenv("GITHUB_APP_CLIENT_ID");
    //        long ttlMillis = 600000;
    //
    //        KeyFactory factory = KeyFactory.getInstance("RSA");
    //
    //        FileReader keyReader = new FileReader("algosolved.private-key.pem");
    //        PemReader pemReader = new PemReader(keyReader);
    //
    //        PemObject pemObject = pemReader.readPemObject();
    //        byte[] content = pemObject.getContent();
    //        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
    //        PrivateKey privateKey = factory.generatePrivate(privKeySpec);
    //
    //
    //        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
    //
    //        long nowMillis = System.currentTimeMillis();
    //        Date now = new Date(nowMillis);
    //
    //        Key signingKey = privateKey;
    //
    //        JwtBuilder builder = Jwts.builder()
    //            .issuedAt(now)
    //            .issuer(appId)
    //            .signWith(signingKey, signatureAlgorithm);
    //
    //        if (ttlMillis > 0) {
    //            long expMillis = nowMillis + ttlMillis;
    //            Date exp = new Date(expMillis);
    //            builder.expiration(exp);
    //        }
    //        return builder.compact();
    //    }
}
