{...}: {
  perSystem = {
    pkgs,
    config,
    ...
  }: {
    # Pin the exact JDK version rather than a floating `jdk`; bump deliberately.
    config.shellPackages = with pkgs; [jdk25 gradle];
  };
}
