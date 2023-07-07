package carrental;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//Javaから提供されている暗号化の処理
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login //ログイン処理を使いますよ
				.loginProcessingUrl("/loginForm")
				.loginPage("/loginForm") //ログインのページに行くにはログインフォームから
				.defaultSuccessUrl("/reserve", true)//ログインしたら商品のページが出ますよ
				.failureUrl("/loginError")//ログイン失敗のとき、ログインエラーフォームに飛ぶ
				.permitAll()//認可処理、誰でもログイン画面にはアクセスすることができますよ
		//↑これでログイン時の設定が完了
		//↓ログアウトの処理をする
		).logout(logout -> logout
				.logoutSuccessUrl("/loginForm")).authorizeHttpRequests(authz -> authz//
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						//↑Springのシステムを使わず、持っていくだけのフォーム
						//staticのやつは全部許可しますよ
						.requestMatchers("/").permitAll()//ルートへのアクセスは誰でもOK
						.requestMatchers("/reserve").permitAll()//レンタカ一覧も全員アクセス許可
						.requestMatchers("/signup").permitAll()//新規登録も全員アクセス許可
						.requestMatchers("/admin/**").hasRole("ADMIN")//管理者権限：**なので、ADMINの場合は全てにアクセス許可
						.anyRequest().authenticated());//その他のリクエスト：権限をチェック,拒否しますよという設定
		return http.build();//httpの構成ファイルを作成してくれる
	}
}

////見本
//@Bean
//public PasswordEncoder passwordEncorder() {
//	return new BCryptPasswordEncoder();
//}
//
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//	http.formLogin(login -> login
//			.loginProcessingUrl("/loginForm")
//			.loginPage("/loginForm")
//			.defaultSuccessUrl("/reserve" ,true)
//			.failureUrl("/loginForm?error")
//			.permitAll()
//	).logout(logout -> logout
//			.logoutSuccessUrl("/loginForm")
//	).authorizeHttpRequests(authz -> authz
//			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//			.requestMatchers("/").permitAll()
//			.requestMatchers("/reserve").permitAll()
//			.requestMatchers("/signup").permitAll()
//			.requestMatchers("/admin/**").hasRole("ADMIN")
//			.anyRequest().authenticated()
//			);
//	return http.build();
//}

