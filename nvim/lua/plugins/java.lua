return {
	{
		"neovim/nvim-lspconfig",
		opts = {
			-- Configure jdtls for Java projects
			servers = {
				jdtls = {
					-- Key settings for OpenJDK 21
					cmd = { "jdtls" },
					root_dir = function(fname)
						return require("lspconfig.util").root_pattern("build.gradle", "pom.xml", ".git", "src")(fname)
							or vim.fn.getcwd()
					end,
					settings = {
						java = {
							configuration = {
								runtimes = {
									{
										name = "JavaSE-21",
										path = "/usr/lib/jvm/java-21-openjdk/", -- Update this path!
										default = true,
									},
								},
							},
							-- Enable auto-imports and better code completion
							completion = {
								favoriteStaticMembers = {
									"org.junit.jupiter.api.Assertions.*",
									"java.util.Objects.*",
								},
							},
							sources = {
								organizeImports = {
									starThreshold = 9999,
									staticStarThreshold = 9999,
								},
							},
						},
					},
				},
			},
		},
	},
	-- Optional: Java-specific plugins
	{
		"mfussenegger/nvim-jdtls", -- Extended Java functionality
		ft = "java",
	},
}
