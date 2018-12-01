package com.itmuch.cloud.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.alibaba.fastjson.JSONObject;

public class GitUtil {
	private static final String git_username = "937557708@qq.com";
	private static final String git_password = "tjr07241711";
	private static String branch_name = "master";
	public static String localRepoPath = "D:/repo";
	// public static String localRepoPath+"/.git" = "D:/repo/.git";
	public static String remoteRepoURI = "https://github.com/a937557708/API.git";
	public static String localCodeDir = "D:/repo1";

	/**
	 * 新建一个分支并同步到远程仓库
	 * 
	 * @param branchName
	 * @throws IOException
	 * @throws GitAPIException
	 */
	public static String newBranch(String branchName) {
		String newBranchIndex = "refs/heads/" + branchName;
		String gitPathURI = "";
		Git git = null;
		try {

			// 检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
			List<Ref> refs = git.branchList().call();
			for (Ref ref : refs) {
				if (ref.getName().equals(newBranchIndex)) {
					System.out.println("Removing branch before");
					git.branchDelete().setBranchNames(branchName).setForce(true).call();
					break;
				}
			}
			// 新建分支
			Ref ref = git.branchCreate().setName(branchName).call();
			// 推送到远程
			git.push().add(ref).call();
			gitPathURI = remoteRepoURI + " " + "feature/" + branchName;
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gitPathURI;
	}

	public static void commitFiles() throws IOException, GitAPIException {
		String filePath = "";
		UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(git_username,
				git_password);
		Git git = Git.open(new File(localRepoPath + "/.git"));
		// 创建用户文件的过程
		File myfile = new File(filePath);
		myfile.createNewFile();
		git.add().addFilepattern("pets").call();
		// 提交
		git.commit().setMessage("Added pets").call();
		// 推送到远程
		git.push().call();
	}

	public static String cloneRepository(String url, String localPath) {
		try {
			System.out.println("开始下载......");
			// 设置远程服务器上的用户名和密码
			UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(git_username,
					git_password);
//			CloneCommand cc = Git.cloneRepository().setURI(url);
//			cc.setDirectory(new File(localPath)).setCredentialsProvider(provider).call();
			Git git = Git.cloneRepository().setURI(remoteRepoURI).setDirectory(new File(localRepoPath))
					.setCredentialsProvider(provider).call();

			System.out.println("下载完成......");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static boolean pullBranchToLocal(String cloneURL) {
		boolean resultFlag = false;
		// String[] splitURL = cloneURL.split(" ");
		// String branchName = splitURL[1];
		String fileDir = localCodeDir;
		// 检查目标文件夹是否存在
		File file = new File(fileDir);
		// if (file.exists()) {
		// deleteFolder(file);
		// }

		Git git;
		try {
			git = Git.open(new File(localRepoPath + "/.git"));

			git.cloneRepository().setURI(cloneURL).setDirectory(file).call();
			resultFlag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultFlag;
	}

	/**
	 * 拉取远程代码
	 * 
	 * @param remoteBranchName
	 * @return 远程分支名
	 */

	public static boolean pull() {
		return pull(branch_name);
	}

	public static boolean pull(String remoteBranchName) {

		boolean pullFlag = true;
		try (Git git = Git.open(new File(localRepoPath + "/.git"));) {
			// UsernamePasswordCredentialsProvider provider =new
			// UsernamePasswordCredentialsProvider(GIT_USERNAME,GIT_PASSWORD);
			git.pull().setRemoteBranchName(remoteBranchName)
					// .setCredentialsProvider(provider)
					.call();
		} catch (Exception e) {
			e.printStackTrace();
			pullFlag = false;
		} finally {
			return pullFlag;
		}
	}

	/**
	 * 创建本地新仓库
	 * 
	 * @param repoPath
	 *            仓库地址 D:/workspace/TestGitRepository
	 * @return
	 * @throws IOException
	 */
	public static Repository createNewRepository(String repoPath) throws IOException {
		File localPath = new File(repoPath);
		// create the directory
		Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
		repository.create();
		return repository;
	}

	public static void deleteFolder(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFolder(files[i]);
				files[i].delete();
			}
		}
	}

	public static void init(JSONObject json) {
		if (json != null) {
			if (json.containsKey("remoteRepoURI")) {
				remoteRepoURI = json.getString("remoteRepoURI");
			}
			if (json.containsKey("localRepoPath")) {
				localRepoPath = json.getString("localRepoPath");
			}
			if (json.containsKey("localCodeDir")) {
				localCodeDir = json.getString("localCodeDir");
			}
			if (json.containsKey("branch_name")) {
				branch_name = json.getString("branch_name");
			}

		}
		File file = new File(localRepoPath + "/.git");
		if (file.exists()) {
			pull();
			// pullBranchToLocal(remoteRepoURI);
		} else {
			cloneRepository(remoteRepoURI, localRepoPath);
		}
	}

	static void setupRepo() throws GitAPIException {
		// 建立与远程仓库的联系，仅需要执行一次
		Git git = Git.cloneRepository().setURI(remoteRepoURI).setDirectory(new File(localRepoPath)).call();
	}

	public static void main(String[] args) {
		GitUtil.init(null);
		// System.out.println("aaa");
	}
}
