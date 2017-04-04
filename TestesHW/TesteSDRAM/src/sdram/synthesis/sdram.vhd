-- sdram.vhd

-- Generated using ACDS version 16.1 196

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity sdram is
	port (
		clk_clk                : in    std_logic                     := '0';             --        clk.clk
		reset_reset_n          : in    std_logic                     := '0';             --      reset.reset_n
		sdram_s1_address       : in    std_logic_vector(23 downto 0) := (others => '0'); --   sdram_s1.address
		sdram_s1_byteenable_n  : in    std_logic_vector(1 downto 0)  := (others => '0'); --           .byteenable_n
		sdram_s1_chipselect    : in    std_logic                     := '0';             --           .chipselect
		sdram_s1_writedata     : in    std_logic_vector(15 downto 0) := (others => '0'); --           .writedata
		sdram_s1_read_n        : in    std_logic                     := '0';             --           .read_n
		sdram_s1_write_n       : in    std_logic                     := '0';             --           .write_n
		sdram_s1_readdata      : out   std_logic_vector(15 downto 0);                    --           .readdata
		sdram_s1_readdatavalid : out   std_logic;                                        --           .readdatavalid
		sdram_s1_waitrequest   : out   std_logic;                                        --           .waitrequest
		sdram_wire_addr        : out   std_logic_vector(12 downto 0);                    -- sdram_wire.addr
		sdram_wire_ba          : out   std_logic_vector(1 downto 0);                     --           .ba
		sdram_wire_cas_n       : out   std_logic;                                        --           .cas_n
		sdram_wire_cke         : out   std_logic;                                        --           .cke
		sdram_wire_cs_n        : out   std_logic;                                        --           .cs_n
		sdram_wire_dq          : inout std_logic_vector(15 downto 0) := (others => '0'); --           .dq
		sdram_wire_dqm         : out   std_logic_vector(1 downto 0);                     --           .dqm
		sdram_wire_ras_n       : out   std_logic;                                        --           .ras_n
		sdram_wire_we_n        : out   std_logic                                         --           .we_n
	);
end entity sdram;

architecture rtl of sdram is
	component sdram_sdram is
		port (
			clk            : in    std_logic                     := 'X';             -- clk
			reset_n        : in    std_logic                     := 'X';             -- reset_n
			az_addr        : in    std_logic_vector(23 downto 0) := (others => 'X'); -- address
			az_be_n        : in    std_logic_vector(1 downto 0)  := (others => 'X'); -- byteenable_n
			az_cs          : in    std_logic                     := 'X';             -- chipselect
			az_data        : in    std_logic_vector(15 downto 0) := (others => 'X'); -- writedata
			az_rd_n        : in    std_logic                     := 'X';             -- read_n
			az_wr_n        : in    std_logic                     := 'X';             -- write_n
			za_data        : out   std_logic_vector(15 downto 0);                    -- readdata
			za_valid       : out   std_logic;                                        -- readdatavalid
			za_waitrequest : out   std_logic;                                        -- waitrequest
			zs_addr        : out   std_logic_vector(12 downto 0);                    -- export
			zs_ba          : out   std_logic_vector(1 downto 0);                     -- export
			zs_cas_n       : out   std_logic;                                        -- export
			zs_cke         : out   std_logic;                                        -- export
			zs_cs_n        : out   std_logic;                                        -- export
			zs_dq          : inout std_logic_vector(15 downto 0) := (others => 'X'); -- export
			zs_dqm         : out   std_logic_vector(1 downto 0);                     -- export
			zs_ras_n       : out   std_logic;                                        -- export
			zs_we_n        : out   std_logic                                         -- export
		);
	end component sdram_sdram;

	component altera_reset_controller is
		generic (
			NUM_RESET_INPUTS          : integer := 6;
			OUTPUT_RESET_SYNC_EDGES   : string  := "deassert";
			SYNC_DEPTH                : integer := 2;
			RESET_REQUEST_PRESENT     : integer := 0;
			RESET_REQ_WAIT_TIME       : integer := 1;
			MIN_RST_ASSERTION_TIME    : integer := 3;
			RESET_REQ_EARLY_DSRT_TIME : integer := 1;
			USE_RESET_REQUEST_IN0     : integer := 0;
			USE_RESET_REQUEST_IN1     : integer := 0;
			USE_RESET_REQUEST_IN2     : integer := 0;
			USE_RESET_REQUEST_IN3     : integer := 0;
			USE_RESET_REQUEST_IN4     : integer := 0;
			USE_RESET_REQUEST_IN5     : integer := 0;
			USE_RESET_REQUEST_IN6     : integer := 0;
			USE_RESET_REQUEST_IN7     : integer := 0;
			USE_RESET_REQUEST_IN8     : integer := 0;
			USE_RESET_REQUEST_IN9     : integer := 0;
			USE_RESET_REQUEST_IN10    : integer := 0;
			USE_RESET_REQUEST_IN11    : integer := 0;
			USE_RESET_REQUEST_IN12    : integer := 0;
			USE_RESET_REQUEST_IN13    : integer := 0;
			USE_RESET_REQUEST_IN14    : integer := 0;
			USE_RESET_REQUEST_IN15    : integer := 0;
			ADAPT_RESET_REQUEST       : integer := 0
		);
		port (
			reset_in0      : in  std_logic := 'X'; -- reset
			clk            : in  std_logic := 'X'; -- clk
			reset_out      : out std_logic;        -- reset
			reset_req      : out std_logic;        -- reset_req
			reset_req_in0  : in  std_logic := 'X'; -- reset_req
			reset_in1      : in  std_logic := 'X'; -- reset
			reset_req_in1  : in  std_logic := 'X'; -- reset_req
			reset_in2      : in  std_logic := 'X'; -- reset
			reset_req_in2  : in  std_logic := 'X'; -- reset_req
			reset_in3      : in  std_logic := 'X'; -- reset
			reset_req_in3  : in  std_logic := 'X'; -- reset_req
			reset_in4      : in  std_logic := 'X'; -- reset
			reset_req_in4  : in  std_logic := 'X'; -- reset_req
			reset_in5      : in  std_logic := 'X'; -- reset
			reset_req_in5  : in  std_logic := 'X'; -- reset_req
			reset_in6      : in  std_logic := 'X'; -- reset
			reset_req_in6  : in  std_logic := 'X'; -- reset_req
			reset_in7      : in  std_logic := 'X'; -- reset
			reset_req_in7  : in  std_logic := 'X'; -- reset_req
			reset_in8      : in  std_logic := 'X'; -- reset
			reset_req_in8  : in  std_logic := 'X'; -- reset_req
			reset_in9      : in  std_logic := 'X'; -- reset
			reset_req_in9  : in  std_logic := 'X'; -- reset_req
			reset_in10     : in  std_logic := 'X'; -- reset
			reset_req_in10 : in  std_logic := 'X'; -- reset_req
			reset_in11     : in  std_logic := 'X'; -- reset
			reset_req_in11 : in  std_logic := 'X'; -- reset_req
			reset_in12     : in  std_logic := 'X'; -- reset
			reset_req_in12 : in  std_logic := 'X'; -- reset_req
			reset_in13     : in  std_logic := 'X'; -- reset
			reset_req_in13 : in  std_logic := 'X'; -- reset_req
			reset_in14     : in  std_logic := 'X'; -- reset
			reset_req_in14 : in  std_logic := 'X'; -- reset_req
			reset_in15     : in  std_logic := 'X'; -- reset
			reset_req_in15 : in  std_logic := 'X'  -- reset_req
		);
	end component altera_reset_controller;

	signal rst_controller_reset_out_reset           : std_logic; -- rst_controller:reset_out -> rst_controller_reset_out_reset:in
	signal reset_reset_n_ports_inv                  : std_logic; -- reset_reset_n:inv -> rst_controller:reset_in0
	signal rst_controller_reset_out_reset_ports_inv : std_logic; -- rst_controller_reset_out_reset:inv -> sdram:reset_n

begin

	sdram : component sdram_sdram
		port map (
			clk            => clk_clk,                                  --   clk.clk
			reset_n        => rst_controller_reset_out_reset_ports_inv, -- reset.reset_n
			az_addr        => sdram_s1_address,                         --    s1.address
			az_be_n        => sdram_s1_byteenable_n,                    --      .byteenable_n
			az_cs          => sdram_s1_chipselect,                      --      .chipselect
			az_data        => sdram_s1_writedata,                       --      .writedata
			az_rd_n        => sdram_s1_read_n,                          --      .read_n
			az_wr_n        => sdram_s1_write_n,                         --      .write_n
			za_data        => sdram_s1_readdata,                        --      .readdata
			za_valid       => sdram_s1_readdatavalid,                   --      .readdatavalid
			za_waitrequest => sdram_s1_waitrequest,                     --      .waitrequest
			zs_addr        => sdram_wire_addr,                          --  wire.export
			zs_ba          => sdram_wire_ba,                            --      .export
			zs_cas_n       => sdram_wire_cas_n,                         --      .export
			zs_cke         => sdram_wire_cke,                           --      .export
			zs_cs_n        => sdram_wire_cs_n,                          --      .export
			zs_dq          => sdram_wire_dq,                            --      .export
			zs_dqm         => sdram_wire_dqm,                           --      .export
			zs_ras_n       => sdram_wire_ras_n,                         --      .export
			zs_we_n        => sdram_wire_we_n                           --      .export
		);

	rst_controller : component altera_reset_controller
		generic map (
			NUM_RESET_INPUTS          => 1,
			OUTPUT_RESET_SYNC_EDGES   => "deassert",
			SYNC_DEPTH                => 2,
			RESET_REQUEST_PRESENT     => 0,
			RESET_REQ_WAIT_TIME       => 1,
			MIN_RST_ASSERTION_TIME    => 3,
			RESET_REQ_EARLY_DSRT_TIME => 1,
			USE_RESET_REQUEST_IN0     => 0,
			USE_RESET_REQUEST_IN1     => 0,
			USE_RESET_REQUEST_IN2     => 0,
			USE_RESET_REQUEST_IN3     => 0,
			USE_RESET_REQUEST_IN4     => 0,
			USE_RESET_REQUEST_IN5     => 0,
			USE_RESET_REQUEST_IN6     => 0,
			USE_RESET_REQUEST_IN7     => 0,
			USE_RESET_REQUEST_IN8     => 0,
			USE_RESET_REQUEST_IN9     => 0,
			USE_RESET_REQUEST_IN10    => 0,
			USE_RESET_REQUEST_IN11    => 0,
			USE_RESET_REQUEST_IN12    => 0,
			USE_RESET_REQUEST_IN13    => 0,
			USE_RESET_REQUEST_IN14    => 0,
			USE_RESET_REQUEST_IN15    => 0,
			ADAPT_RESET_REQUEST       => 0
		)
		port map (
			reset_in0      => reset_reset_n_ports_inv,        -- reset_in0.reset
			clk            => clk_clk,                        --       clk.clk
			reset_out      => rst_controller_reset_out_reset, -- reset_out.reset
			reset_req      => open,                           -- (terminated)
			reset_req_in0  => '0',                            -- (terminated)
			reset_in1      => '0',                            -- (terminated)
			reset_req_in1  => '0',                            -- (terminated)
			reset_in2      => '0',                            -- (terminated)
			reset_req_in2  => '0',                            -- (terminated)
			reset_in3      => '0',                            -- (terminated)
			reset_req_in3  => '0',                            -- (terminated)
			reset_in4      => '0',                            -- (terminated)
			reset_req_in4  => '0',                            -- (terminated)
			reset_in5      => '0',                            -- (terminated)
			reset_req_in5  => '0',                            -- (terminated)
			reset_in6      => '0',                            -- (terminated)
			reset_req_in6  => '0',                            -- (terminated)
			reset_in7      => '0',                            -- (terminated)
			reset_req_in7  => '0',                            -- (terminated)
			reset_in8      => '0',                            -- (terminated)
			reset_req_in8  => '0',                            -- (terminated)
			reset_in9      => '0',                            -- (terminated)
			reset_req_in9  => '0',                            -- (terminated)
			reset_in10     => '0',                            -- (terminated)
			reset_req_in10 => '0',                            -- (terminated)
			reset_in11     => '0',                            -- (terminated)
			reset_req_in11 => '0',                            -- (terminated)
			reset_in12     => '0',                            -- (terminated)
			reset_req_in12 => '0',                            -- (terminated)
			reset_in13     => '0',                            -- (terminated)
			reset_req_in13 => '0',                            -- (terminated)
			reset_in14     => '0',                            -- (terminated)
			reset_req_in14 => '0',                            -- (terminated)
			reset_in15     => '0',                            -- (terminated)
			reset_req_in15 => '0'                             -- (terminated)
		);

	reset_reset_n_ports_inv <= not reset_reset_n;

	rst_controller_reset_out_reset_ports_inv <= not rst_controller_reset_out_reset;

end architecture rtl; -- of sdram
