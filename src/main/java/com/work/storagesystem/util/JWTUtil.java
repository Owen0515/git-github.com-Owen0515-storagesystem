package com.work.storagesystem.util;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.work.storagesystem.exception.UnauthorizedException;

import jakarta.annotation.PostConstruct;

@Component
public class JWTUtil {

	/**
	 * 密鑰
	 */
	@Value("${jwt.secret}")
	private String secret ;

	/**
	 * 默認 token 過期時間 => 60分鐘
	 */
	private long DEFAULT_EXPIRATION_TIME = 1000 * 60 * 60;

	@PostConstruct
	public void init() {
		if (secret == null || secret.isEmpty()) {
			throw new UnauthorizedException("JWT secret key is not configured");
		}
	}

	/**
	 * 生成 JWT Token
	 * @param data
	 * @return
	 */
	public String createToken(String data) {

		long expirationTime =DEFAULT_EXPIRATION_TIME;

		try {
			// 建構 JWT 主體
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().issuer("system").issueTime(new Date())
					.expirationTime(new Date(System.currentTimeMillis() + expirationTime)).subject(data).build();

			JWSSigner signer = new MACSigner(secret);
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
			signedJWT.sign(signer);

			return signedJWT.serialize();
		} catch (JOSEException e) {
			throw new UnauthorizedException("Error generating JWT token");
		}
	}

	/**
	 * 驗證 JWT Token
	 *
	 * @param token JWT Token
	 * @return 提取的存入的 data
	 */
	public JWTClaimsSet validateAndParseToken(String token) {
		try {
			// 解析 Token
			SignedJWT signedJWT = SignedJWT.parse(token);

			// 驗證簽名
			JWSVerifier verifier = new MACVerifier(secret);
			if (!signedJWT.verify(verifier)) {
				throw new UnauthorizedException("Invalid JWT signature");
			}
			// 驗證是否過期
			Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
			if (expirationTime == null || expirationTime.before(new Date())) {
				throw new UnauthorizedException("JWT token has expired");
			}
			// 驗證成功，返回存入的 data
			JWTClaimsSet data = signedJWT.getJWTClaimsSet();
			if (data == null) {
				throw new UnauthorizedException("No data found in JWT token");
			}
			return data;
		} catch (ParseException e) {
			throw new UnauthorizedException("Error parsing JWT token");
		} catch (JOSEException e) {
			throw new UnauthorizedException("Error verifying JWT signature");
		}

	}

	/**
	 * 從 Token 中驗證並提取 userId
	 *
	 * @param token JWT Token
	 * @return userId (Integer)
	 */
	public Long getUserIdFromToken(JWTClaimsSet data) {
		String dataStr = data.getSubject();

		if (dataStr == null || dataStr.isEmpty()) {
			throw new UnauthorizedException("Unable to parse or invalid JWT token.");
		}
		// 解析為 JSONObject
		JSONObject dataJson;
		try {
			dataJson = new JSONObject(dataStr);
		} catch (Exception e) {
			throw new UnauthorizedException("Data in the token is not a valid JSON format.");
		}
		// 獲取 userId
		if (!dataJson.has("userId")) {
			throw new UnauthorizedException("Token does not contain userId.");
		}
		Long userId;
		try {
			userId = dataJson.getLong("userId");
		} catch (Exception e) {
			throw new UnauthorizedException("userId in the token is not a valid numeric format.");
		}
		return userId;
	}

}
