{...}: {
  perSystem = {
    pkgs,
    config,
    ...
  }: {
    config.shellPackages = with pkgs; [jdk25 gradle];
  };
}
