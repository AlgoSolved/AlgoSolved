/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: "ts-jest",
  // testEnvironment: "node",
  rootDir: ".",
  testMatch: ["**/?(*)+(spec).tsx"],
  resetMocks: true,
  clearMocks: true,

  // moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths, {
  //   // rootDir is the root of the directory containing `jest config file` or the `package.json`
  //   prefix: "<rootDir>",
  // }),

  roots: ["<rootDir>"],
  transform: {
    "^.+\\.(t|j)sx?$": "ts-jest",
  },
  moduleFileExtensions: ["ts", "js", "tsx", "json", "node"],
  collectCoverage: true,
  clearMocks: true,
  coverageDirectory: "coverage",
};
