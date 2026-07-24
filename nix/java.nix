{...}: {
  perSystem = {
    pkgs,
    config,
    ...
  }: {
    config = {
      shellPackages = with pkgs; [jdk25 gradle google-java-format];
      shellHooks = [
        ''
          ln -sfn ${pkgs.jdk25.home} .java_nix_bin_symlink
        ''
      ];
    };
  };
}
